import React from 'react'
import axios from 'axios';
import {Link} from 'react-router-dom';
import { Table, Button, Divider, message, Typography} from 'antd';
import FormModalActions from './forms/invoiceForm/FormModalActions';
const {Title} = Typography;

class ItemsList extends React.Component {
  state = {
    filteredInfo: null,
    sortedInfo: null,
    items: []
  };

  componentWillMount = () => {
    axios.get("http://localhost:8080/api/invoice")
          .then(promise =>{
            this.setState({
              invoices: promise.data
            })
            console.log(this.state.items)
          })
          .catch(() =>{
            message.error("Couldnt connect to database to get your items", 0);
          })
  }

  handleInvoiceDelete = (id) =>{
    axios.delete(`http://localhost:8080/api/invoice/${id}`)
          .then(() =>{
            this.componentWillMount();
            message.info('Invoice was deleted succesfully.');
          })
          .catch(()=>{
            message.error("Whoopsie, something went wrong, couldnt delete selected invoice :(", 10);
          })
  }

  handleChange = (pagination, filters, sorter) => {
    console.log('Various parameters', pagination, filters, sorter);
    this.setState({
      filteredInfo: filters,
      sortedInfo: sorter,
    });
  }

  clearFilters = () => {
    this.setState({ filteredInfo: null });
  }

  clearAll = () => {
    this.setState({
      filteredInfo: null,
      sortedInfo: null,
    });
  }

  nameSorter(a, b){
    const aCase = a.toLowerCase();
    const bCase = b.toLowerCase();
    if(aCase < bCase) { return -1; }
    if(aCase > bCase) { return 1; }
    return 0;
  }

  render() {
    let { sortedInfo, filteredInfo } = this.state;
    sortedInfo = sortedInfo || {};
    filteredInfo = filteredInfo || {};
    const columns = [{
      title: 'Name',
      dataIndex: 'name',
      key: 'name',
      filters: this.state.invoices.map(({name}) => ({
        text: name, value: name
      })),
      filteredValue: filteredInfo.name || null,
      onFilter: (value, record) => record.name.includes(value),
      sorter: (a, b) => this.nameSorter(a.name, b.name),
      sortOrder: sortedInfo.columnKey === 'name' && sortedInfo.order,
    }, {
      title: 'Quantity',
      dataIndex: 'quantity',
      key: 'quantity',
      sorter: (a, b) => a.quantity - b.quantity,
      sortOrder: sortedInfo.columnKey === 'quantity' && sortedInfo.order,
    }, {
      title: 'Height (cm)',
      dataIndex: 'heightCm',
      key: 'heightCm',
      sorter: (a, b) => a.heightCm - b.heightCm,
      sortOrder: sortedInfo.columnKey === 'heightCm' && sortedInfo.order,
    }, {
      title: 'Width (cm)',
      dataIndex: 'widthCm',
      key: 'widthCm',
      sorter: (a, b) => a.widthCm - b.widthCm,
      sortOrder: sortedInfo.columnKey === 'widthCm' && sortedInfo.order,
    }, {
      title: 'Action',
      key: 'action',
      render: (text, record) => (
        <span>
          <Link to={{
            pathname: '/' + record.id,
          }}>Edit invoice</Link>
          <Divider type='vertical' />
          <Link to={{
            pathname:'/item/' + record.id
            }}>Show items</Link>
          <Divider type="vertical" />
          <Button type='danger' onClick={()=> this.handleInvoiceDelete(record.id)}>Delete</Button>
        </span>
      ),
    }];
    return (
      <div>
        <Title>Invoices list</Title>
        <div className="table-operations">
          <FormModalActions refreshData = {this.componentWillMount} />
          <Button onClick={this.clearFilters}>Clear filters</Button>
          <Button onClick={this.clearAll}>Clear filters and sorters</Button>
        </div>
        <Table 
          rowKey={record=>record.id} 
          columns={columns} 
          dataSource={this.state.invoices} 
          onChange={this.handleChange} 
          pagination={{pageSize: 8}}
        />
      </div>
    );
  }
}

export default ItemsList;