import React from 'react';
import axios from 'axios';
import {Form, Input, Button, message, Select} from 'antd';
const {Option} = Select;
  
  class EditItemClass extends React.Component {
    
    state = {
      categories:[],
      unitsOfMeasurement:[]
    }

    componentWillMount() {
      axios.get('/items/categories')
        .then(response =>{
          this.setState({
            categories: response.data
          })
          console.log(response)
      })
      axios.get('/items/unitsOfMeasurement')
        .then(response =>{
          this.setState({
            unitsOfMeasurement: response.data
          })
        })
        console.log(this.props.match.params.id);
    }

    handleSubmit = (e) => {
      e.preventDefault();
      this.props.form.validateFields((err, values) => {
        if (!err) {
          console.log('Received values of form: ', values);
          axios.put('/items'+ this.props.match.params.id, values)
          .then(()=>{
            message.info(`Item ${values.name} was edited.`)
            this.props.history.push('/items')
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
          <Form.Item label="Price">
            {getFieldDecorator('price', {
              rules: [
                {
                  required: true,
                  pattern: new RegExp("^[0-9]+(\.[0-9]{1,2})?$"),
                  message: 'Please input valid price value.',
                },
            ],
            })(<Input placeholder='0.00'/>)}
            
          </Form.Item>
          <Form.Item label="Unit of measurement">
            {getFieldDecorator('unitOfMeasurement', {
              rules: [{
                required: true,
                }
            ],
            })(<Select
                style={{ width: '32%' }}
              >
              {
                  this.state.unitsOfMeasurement.map(unit =>
                  <Option key={unit} value={unit}>{unit}</Option>
                  )
              }
              </Select>)}
            
          </Form.Item>
          <Form.Item label="Category">
            {getFieldDecorator('category', {
              rules: [{
                required: true,
                }
            ],
            })(<Select
                style={{ width: '32%' }}
              >
                {
                  this.state.categories.map(category =>
                  <Option key={category} value={category}>{category}</Option>
                  )
                }
              </Select>)}
            
          </Form.Item>
          <Form.Item>
            <Button type="primary" htmlType="submit">Submit</Button>
          </Form.Item>
          <Form.Item>
            <Button type='default' onClick={(props)=>this.props.history.push('/items')}>Cancel</Button>
          </Form.Item>
        </Form>
      );
    }
  }
  
  const EditItemForm = Form.create()(EditItemClass);
  export default EditItemForm;
