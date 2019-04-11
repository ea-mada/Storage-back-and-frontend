import React from 'react'

const Alert = (props)=> {
  return (
    <div className="alert alert-warning alert-dismissible fade show" role="alert">
        <strong>{props.alertText}</strong>
        <button type="button" className="close" data-dismiss="alert" aria-label="Close" onClick={props.onClose}>
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
  )
}

export default Alert;