import React from 'react';
import axios from 'axios';
import {Form, Input, Button, message, Select} from 'antd';
const {Option} = Select;
import ItemForm from './ItemForm';
  
  class EditItemClass extends React.Component {

    render() {
      return(
        <ItemForm id={this.props.match.params.id}/>
      );
    }
  }
  
  const EditItemForm = Form.create()(EditItemClass);
  export default EditItemForm;
