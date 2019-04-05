import axios from 'axios';

export function savePfile(pfile){
    console.log(pfile);
    return axios.post('http://localhost:8080/upmureport/upmu', pfile);    
}

export function getPfile(dirId){
    console.log(typeof dirId);
    return axios.get(`http://localhost:8080/upmureport/upmu/${dirId}`);    
}

export function updatePfile(pfile){
    console.log(pfile);
    return axios.put('http://localhost:8080/upmureport/upmu', pfile);    
}

export function deletePfile(pfileId){
    console.log(pfileId);
    return axios.delete(`http://localhost:8080/upmureport/upmu/${pfileId}`);
}