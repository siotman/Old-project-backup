import React, { Component } from 'react';

import store from '../stores'
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as projectActions from '../stores/modules/projectState';

import { ProjPanel } from '../components/ProjPanel';

class ProjPanelContainer extends Component {
    constructor(props) {
        super(props);
        this.loadDirs = this.loadDirs.bind(this);
        this.handleDirItemClick = this.handleDirItemClick.bind(this);
        this.handleDirItemActionCall = this.handleDirItemActionCall.bind(this);
    }
    componentDidMount() {
        const { userState } = store.getState();
        const { ProjectActions } = this.props;
        ProjectActions.axiosGetAsync('api/projects/list', {userId: userState.selectedUser.userId});
    }
    
    componentWillUpdate(prevProps, prevState) {  //여러번 바뀌어야할때 전 상태와 현재 상태를 비교해서 업데이트 
        const { userState } = store.getState();
        const { ProjectActions } = this.props;
        if (prevProps.userState.selectedUser === this.props.userState.selectedUser) return false;
        else {
            ProjectActions.axiosGetAsync('api/projects/list', {userId: userState.selectedUser.userId});
        }
    }

    loadDirs (projId) {
        const { userState } = store.getState();
        const { ProjectActions } = this.props;

        ProjectActions.axiosPostAsync('api/projects/dirs', {projId, userId: userState.selectedUser.userId})
    }
    
    handleDirItemClick (selectedDirId) {
        const { ProjectActions } = this.props;
        ProjectActions.saveDirId(selectedDirId);
    };
    
    handleDirItemActionCall (endPoint, projId, selectedDirId, item) {
        const { ProjectActions } = this.props;
        const { userState } = store.getState();

        ProjectActions.axiosPostAsync(endPoint, {
            projId,
            userId: userState.userInfo.userId,
            selectedDirId,
            item
        });
    }


    render() {
        const { projectState } = store.getState();
        const { ProjectActions } = this.props;
        //임시 유저 목록 스토어
        
        const { userState } = store.getState();
        const breadcrumb = [userState.selectedUser.userName, "내 프로젝트"];
        
        if(!breadcrumb) return <div></div>
        return (<ProjPanel 
                    projectState={projectState} 
                    userState={userState}
                    breadcrumb={breadcrumb}
                    onProjClick={this.loadDirs}
                    ProjectActions={ProjectActions}
                    handleDirItemClick={this.handleDirItemClick}
                    handleDirItemActionCall={this.handleDirItemActionCall}
                    ></ProjPanel>);
    }
}

export default connect(
    (state) => ({
        projectState: state.projectState,
        userState: state.userState,
    }),
    (dispatch) => ({
        ProjectActions: bindActionCreators(projectActions, dispatch)
    })
) (ProjPanelContainer);