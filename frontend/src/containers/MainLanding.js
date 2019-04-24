import React, { Component } from 'react';

class DashboardLanding extends Component {

    render() {
        return (<>
            <div className="jumbotron shadow" style={{ height: '100%', backgroundColor: 'white', color: 'black' }}>
                <h1 className="display-4 font-weight-bold">HELLO, UPMUREPORT-WEB!</h1>
                <p className="lead">
                    안녕하세요, 업무리포트 웹버전 프로젝트 관리 패널입니다.
                </p>
                <hr className="my-4"/>
                <p> 좌측 사이드 바에서 프로젝트를 추가하시거나, 관리하실 수 있습니다. </p>
                <p> 좀 더 자세한 튜토리얼/가이드를 원하신다면 아래 버튼을 클릭해 문서를 다운로드해주세요. </p>
                
                <br/>
                <button className="btn btn-primary">가이드 문서 다운로드 하기</button>
            </div>
        </>);
    }
}

export default DashboardLanding;