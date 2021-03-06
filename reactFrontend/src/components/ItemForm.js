import React from 'react';
import axios from 'axios';
import {Form, Input, Button, message, Select} from 'antd';
const {Option} = Select;

import { withRouter } from 'react-router-dom';
  
  class ItemsForm extends React.Component {
    
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

        if (this.props.id !== 'form') {
          axios.get('/items/getItem/'+ this.props.id)
        .then((res) => {
          this.props.form.setFieldsValue({
            name: res.data.name,
            category: res.data.category,
            price: res.data.price,
            unitOfMeasurement: res.data.unitOfMeasurement
          });
          
        });
        }
    }

    handleSubmit = (e) => {
      e.preventDefault();
      this.props.form.validateFields((err, values) => {
        if (!err) {
          console.log('Received values of form: ', values);

          if (this.props.id === 'form') {
            axios.post('/items/addItem', values)
          .then(()=>{
            message.info(`Item ${values.name} was submited.`)
            this.props.history.push('/items')
          })
          } else {
            axios.put('/items/'+ this.props.id, values)
          .then(()=>{
            message.info(`Item ${values.name} was edited.`)
            this.props.history.push('/items')
          })
          }
          
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
  
  const ItemForm = Form.create()(ItemsForm);
  export default withRouter(ItemForm);
