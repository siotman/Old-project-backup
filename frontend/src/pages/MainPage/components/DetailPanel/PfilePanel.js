import React, { Component } from 'react';

class PfilePanel extends Component {

    state = {
        isUpdate: false,
    }

    onClickUpdateBts = (e) => {

        e.preventDefault();
        const { handleUpdateBts } = this.props;
        handleUpdateBts({ detailViewLevel: 'pfileUpdate' });
    }

    onClickCancleBts = (e) => {
        e.preventDefault();
        const { handleUpdateBts } = this.props;
        handleUpdateBts({ detailViewLevel: 'pfileUpdate' });
    }

    render() {

        const pfile = this.props.pfileState.get('pfile');

        return (

            <div>
                <div className="card-header py-3">
                    <div className="m-0 font-weight-bold text-dark-1" style={{fontSize:'25px'}}>
                        업무
                    </div>
                </div>

                <div className="card-body">
                    <div className="row">
                        <div className="col-2">
                            <div className="font-weight-bold text-dark-1" style={{textAlign: "center"}}>제목</div>
                        </div>
                        <div className="col-10">
                            <textarea className="form-control text-dark-1 " readOnly rows='1' style={{ resize: 'none' }} value={pfile.name}/>
                        </div>
                    </div>

                    <hr />

                    <div className="row">
                        <div className="col-2">
                            <div className="font-weight-bold text-dark-1"  style={{textAlign: "center"}}>내용</div>
                        </div>
                        <div className="col-10">
                            <textarea className="form-control text-dark-1" rows="9" readOnly value={pfile.contents} style={{ resize: 'none' }}/>
                        </div>
                    </div>


                    <div className="row justify-content-end mr-1">
                        <div className="btn-group" role="group" aria-label="Basic example">
                            <button type="button" className="btn btn-dark-1 p-2 mt-3" onClick={this.onClickUpdateBts}>
                                수정 하기
                            </button>
                        </div>
                    </div>
                </div>
            </div>

        );
    }
}

export default PfilePanel;
