import React from 'react';
import axios from 'axios';
import {Form, Input, Button, message} from 'antd';
  
  class ItemsForm extends React.Component {
    
    handleSubmit = (e) => {
      e.preventDefault();
      this.props.form.validateFields((err, values) => {
        if (!err) {
          console.log('Received values of form: ', values);
          axios.post('/items/addItem', values)
          .then(()=>{
            message.info(`Item ${values.name} was submited.`)
            this.props.history.push('/')
          })
        }
      });
      
    }
  
    render() {
      const { getFieldDecorator } = this.props.form;
      return (
        <Form layout="vertical" onSubmit={this.handleSubmit}>
          <Form.Item label="Name">
            {getFieldDecorator('name', {
              rules: [{ required: true, message: 'Please input item name.' }],
            })(<Input />)}
            
          </Form.Item>
          <Form.Item label="Quantity">
            {getFieldDecorator('quantity', {
              rules: [
                {
                  required: true,
                  type: 'integer',
                  message: 'Please input valid number value.',
                  transform(value) {
                    return Number(value)}
                  }
            ],
            })(<Input placeholder='cm'/>)}
            
          </Form.Item>
          <Form.Item label="Height">
            {getFieldDecorator('height', {
              rules: [{
                required: true,
                type: 'float',
                message: 'Please input valid number value.',
                transform(value) {
                  return Number(value)}
                }
            ],
            })(<Input placeholder='cm'/>)}
            
          </Form.Item>
          <Form.Item label="Width">
            {getFieldDecorator('width', {
              rules: [{
                required: true,
                type: 'float',
                message: 'Please input valid number value.',
                transform(value) {
                  return Number(value)}
                }
            ],
            })(<Input />)}
            
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit">Submit</Button>
          </Form.Item>
        </Form>
      );
    }
  }
  
  const ItemForm = Form.create()(ItemsForm);
  export default ItemForm;