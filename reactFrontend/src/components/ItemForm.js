import React from 'react';
import axios from 'axios';
import {Form, Input, Button, message, Select} from 'antd';
const {Option} = Select;

 class ItemsForm extends React.Component {

   state = {
     categories:[]
   }

   componentWillMount(){
     axios.get('/items/categories')
       .then(response =>{
         console.log(response.data);
         this.setState({
           categories: response.data
         })
       })
   }

   handleSubmit = (e) => {
     e.preventDefault();
     this.props.form.validateFields((err, values) => {
       if (!err) {
         console.log('Received values of form: ', values);
         axios.post('/items/addItem', values)
         .then(()=>{
           message.info(`Item ${values.name} was submited.`)
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
                 type: 'float',
                 message: 'Please input a valid price value.',
                 transform(value) {
                   return Number(value)}
                 }
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
               <Option value="kg">kg</Option>
               <Option value="g">g</Option>
               <Option value="piece">piece</Option>
               <Option value="l">l</Option>
               <Option value="ml">ml</Option>
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
               {this.state.categories.map((category) =>{
                 <Option value={`${category}`}>{`${category}`}</Option>
               })}
               {/* <Option value="CATEGORY1">CATEGORY1</Option>
               <Option value="CAT2">CAT2</Option>
               <Option value="CA3">CA3</Option>
               <Option value="DFBUVUBVIDUVHSOI">DFBUVUBVIDUVHSOI</Option>
               <Option value="DFDFDHYJUKERETRE">DFDFDHYJUKERETRE</Option> */}

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
 export default ItemForm;