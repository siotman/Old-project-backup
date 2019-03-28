package com.nastech.upmureport.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nastech.upmureport.domain.dto.DirDto;
import com.nastech.upmureport.domain.dto.ProjectDto;
import com.nastech.upmureport.domain.dto.UserProjectDto;
import com.nastech.upmureport.domain.entity.Dir;
import com.nastech.upmureport.domain.entity.ProjStat;
import com.nastech.upmureport.domain.entity.Project;
import com.nastech.upmureport.domain.entity.User;
import com.nastech.upmureport.domain.entity.UserProject;
import com.nastech.upmureport.domain.repository.DirRepository;
import com.nastech.upmureport.domain.repository.ProjectRepository;
import com.nastech.upmureport.domain.repository.UserProjectRepository;
import com.nastech.upmureport.domain.repository.UserRepository;

@Service
public class ProjectService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	UserProjectRepository userProjectRepository;
	
	@Autowired
	DirRepository dirRepository;
	
	/**
	 * @param projectDto 프로젝트 등록할 정보
	 * 넘겨받은 프로젝트 정보에 의거해 프로젝트를 등록합니다. 이 때 요청한 유저는 자동적으로 프로젝트에 소속합니다.
	 * @param userDto 등록을 요청한 사용자
	 * @param projStat 프로젝트 상태 
	 * @return 등록자와 프로젝트 연결 객체, 해당 유저가 존재하지 않을 경우 NULL
	 */
	@Transactional
	public UserProject register(ProjectDto projectDto) throws NoSuchElementException {
		User user = null;
		try {
			user = userRepository.findById(projectDto.getUserId()).get();
		} catch (NoSuchElementException nsee) {
			throw new NoSuchElementException("userId에 해당하는 사용자가 없습니다.");
		}
		
		Project project = projectDto.toEntity();
		System.out.println(project);
		project.setCreatedDate(new Date(System.currentTimeMillis())); 
		projectRepository.save(project);
		
		ProjStat pj;
		try {
			System.out.println(projectDto.getProjStat());
			pj = ProjStat.valueOf(projectDto.getProjStat());
		} catch (IllegalArgumentException iae) {
			pj = ProjStat.대기;
		}
		
		UserProject userProject = UserProject.builder()
				.user(user)
				.project(project)
				.projStat(pj)
				.build();
		
		return userProjectRepository.save(userProject);
	}
	

	/**
	 * 프로젝트 DTO와 UserProjectDTO로부터 기존 내용과 다른 사항을 수정합니다.
	 * @param projectDto
	 * @return 변경한 UserProject 오브젝트
	 */
	@Transactional
	public UserProject update(ProjectDto projectDto, UserProjectDto userProjectDto) {
		
		//원본 유저, 프로젝트
		UserProject userProj = userProjectRepository.getOne(userProjectDto.getUserProjectPK());
		
		Project proj = projectDto.toEntity();
		proj = projectRepository.save(proj);
		
		userProj = UserProject.builder()
				.project((Project) Hibernate.unproxy(proj))
				.user((User) Hibernate.unproxy(userProj.getUser()))
				.projStat(userProjectDto.getProjStat())
				.build();
		userProj = userProjectRepository.save(userProj);
		return userProj;
	}
	
	public List<ProjectDto> findProjectsByUserId(String userId) {
		List<UserProject> userProjs = userProjectRepository.findAllByUser(userId);
		List<ProjectDto> projects = new ArrayList<ProjectDto>(); 
		for (UserProject up : userProjs) {
			ProjectDto project = new ProjectDto(up);
			projects.add(project);
		}
		
		return projects;
	}

	public List<Project> findAll() {
		return projectRepository.findAll();
	}	
	
	public Project findOneById(Integer projId) throws NoSuchElementException {
		return projectRepository.findById(projId).get();
	}


	public Project disableProject(Integer projId) throws NoSuchElementException {
		Project proj = findOneById(projId);
		proj.setDeleteFlag(true);
		return projectRepository.save(proj);
	}

	public void deleteProject(Integer projId) {
		Project project = projectRepository.getOne(projId);
		List<UserProject> relatedUpList = userProjectRepository.findAllByProject(project);
		
		userProjectRepository.deleteAll(relatedUpList);
		projectRepository.deleteById(projId);
	}

	public UserProject findUserProjectByProjIdAndUserId(Integer projId, String userId) throws NoSuchElementException {
		UserProject up = userProjectRepository.findOneByUserIdAndProjId(projId, userId);
		if (up == null) throw new NoSuchElementException();
		return up;
	}
	
	public void disableUserProject(Integer projId, String userId) throws NoSuchElementException {
		UserProject up = findUserProjectByProjIdAndUserId(projId, userId);
		up.setDeleteFlag(true);
		userProjectRepository.delete(up);		
	}

	
	/**
	 * 
	 * @param userId
	 * @return 유저 아이디에 연관한 모든 유저-프로젝트 리스트
	 */
	public List<UserProject> findAllUserProjectByUserId(String userId) {
		List<UserProject> userProjs = new ArrayList<UserProject>();
		
		userProjs = userProjectRepository.findAllByUser(userId);
		return userProjs;
	}
	
	/**
	 * 
	 * @param projId
	 * @return
	 */
	public List<UserProject> findAllUserProjectByProjId(Integer projId) {
		List<UserProject> userProjs = new ArrayList<UserProject>();
		
		userProjs = userProjectRepository.findAllByProject(
				Project.builder().projId(projId).build());
		return userProjs;
	}

	
	public List<DirDto> findDirsByProjId(String projId) {
		List<Dir> dirs = dirRepository.findAllByParentProjId(Integer.parseInt(projId));
		List<DirDto> dirDtos = new ArrayList<DirDto>();
		for (Dir dir : dirs) {
			DirDto temp = DirDto.builder()
					.projId(projId)
					.userId(dir.getUser().getUserId())
					.userName(dir.getUser().getUserName())
					.dirId(""+dir.getDirId())
					.dirName(dir.getDirName())
					.build();
			
			if (dir.getParentDir() == null) {
				temp.setParentDirId("root");				
			} else {
				temp.setParentDirId(""+dir.getParentDir().getDirId());
			}
			dirDtos.add(temp);
		}
		return dirDtos;
	}

	public Dir registerDir(DirDto dirDto) {
		String userId = dirDto.getUserId();
		Integer projId = Integer.parseInt(dirDto.getParentProjId());
		Integer parentDirId = Integer.parseInt(dirDto.getParentDirId());
		
		User user = userRepository.findById(userId).get();
		Project proj = projectRepository.findById(projId).get();
		Optional<Dir> parentDir = dirRepository.findById(parentDirId);
		
		Dir dir = Dir.builder()
				.dirName(dirDto.getDirName())
				.user(user)
				.project(proj)
				.build();
		
		if(parentDir.isPresent()) dir.setParentDir(parentDir.get());
		return dirRepository.save(dir);
	}

}
