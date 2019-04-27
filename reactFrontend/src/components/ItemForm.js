import React from 'react';
import {Form, Input, Button} from 'antd';
  
  class ItemForm extends React.Component {
    
    handleSubmit = (e) => {
      e.preventDefault();
      this.props.form.validateFields((err, values) => {
        if (!err) {
          console.log('Received values of form: ', values);
        }
      });
    }
  
    render() {
      const { getFieldDecorator } = this.props.form;
      return (
        <Form layout="inline" onSubmit={this.handleSubmit}>
          <Form.Item label="Name">
            {getFieldDecorator('name', {
              rules: [{ required: true, message: 'Please input item name.' }],
            })}
            <Input />
          </Form.Item>
          <Form.Item label="Quantity">
            {getFieldDecorator('quantity', {
              rules: [{ required: true, message: 'Please input quantity.' },
                {type: integer, message: 'Please input correct number of type integer.'}
            ],
            })}
            <Input placeholder='cm'/>
          </Form.Item>
          <Form.Item label="Height">
            {getFieldDecorator('height', {
              rules: [{ required: true, message: 'Please input height.' },
                {type: float, message: 'Please input correct number of type float.'}
            ],
            })}
            <Input placeholder='cm'/>
          </Form.Item>
          <Form.Item label="Width">
            {getFieldDecorator('width', {
              rules: [{ required: true, message: 'Please input width.' },
                {type: float, message: 'Please input correct number of type float.'}
            ],
            })}
            <Input />
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit">Submit</Button>
          </Form.Item>
        </Form>
      );
    }
  }
  
  const ItemForm = Form.create({ name: 'customized_form_controls' })(ItemForm);
  export default ItemForm;