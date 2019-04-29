import React from 'react'
import axios from 'axios';
import {Link} from 'react-router-dom';
import { Table, Button, Divider, message, Typography, Layout, Icon} from 'antd';
const {Title} = Typography;
const {Header, Content} = Layout;

class ItemsList extends React.Component {
  state = {
    filteredInfo: null,
    sortedInfo: null,
    items: []
  };

  componentWillMount = () => {
    axios.get("/items/getItems")
          .then(response =>{
            this.setState({
              items: response.data
            })
            console.log(this.state.items)
          })
          .catch(() =>{
            message.error("Couldnt connect to database to get your items", 0);
          })
  }

  handleItemDelete = (id) =>{
    axios.delete(`/item/${id}`)
          .then(() =>{
            this.componentWillMount();
            message.info('Item was deleted succesfully.');
          })
          .catch(()=>{
            message.error("Whoopsie, something went wrong, couldnt delete selected item :(", 10);
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
      filters: this.state.items.map(({name}) => ({
        text: name, value: name
      })),
      filteredValue: filteredInfo.name || null,
      onFilter: (value, record) => record.name.includes(value),
      sorter: (a, b) => this.nameSorter(a.name, b.name),
      sortOrder: sortedInfo.columnKey === 'name' && sortedInfo.order,
    }, {
      title: 'Category',
      dataIndex: 'category',
      key: 'category',
      filters: this.state.items.map(({category}) => ({
        text: category, value: category
      })),
      filteredValue: filteredInfo.category || null,
      onFilter: (value, record) => record.category.includes(value),
      sorter: (a, b) => this.nameSorter(a.category, b.category),
      sortOrder: sortedInfo.columnKey === 'category' && sortedInfo.order,
    }, {
      title: 'Unit of measurement',
      dataIndex: 'unitOfMeasurement',
      key: 'unitOfMeasurement',
      filters: this.state.items.map(({unitOfMeasurement}) => ({
        text: unitOfMeasurement, value: unitOfMeasurement
      })),
      filteredValue: filteredInfo.unitOfMeasurement || null,
      onFilter: (value, record) => record.unitOfMeasurement.includes(value),
      sorter: (a, b) => this.nameSorter(a.unitOfMeasurement, b.unitOfMeasurement),
      sortOrder: sortedInfo.columnKey === 'unitOfMeasurement' && sortedInfo.order,
    }, {
      title: 'Price',
      dataIndex: 'price',
      key: 'price',
      sorter: (a, b) => a.price - b.price,
      sortOrder: sortedInfo.columnKey === 'price' && sortedInfo.order,
    }, {
      title: 'Action',
      key: 'action',
      render: (text, record) => (
        <span>
          <Link to={{
            pathname: '/' + record.id,
          }}>Edit item</Link>
          <Divider type="vertical" />
          <Button type='danger' onClick={()=> this.handleItemDelete(record.id)}>Delete</Button>
        </span>
      ),
    }];
    return (
      <div>
      {/* <Layout> */}
        <Header>
          <Button type='primary' onClick={()=>this.props.history.push('/')}>
            <Icon type="left" />Back
          </Button>
          <Divider type="vertical" />
          <Button type='primary' onClick={()=>this.props.history.push('/items/form')} >
            New Item
          </Button>
        </Header>
        {/* <Content> */}
          <Title>Items list</Title>
          <div className="table-operations">
            <Button onClick={this.clearFilters}>Clear filters</Button>
            <Button onClick={this.clearAll}>Clear filters and sorters</Button>
          </div>
          <Table 
            rowKey={record=>record.itemId} 
            columns={columns} 
            dataSource={this.state.items} 
            onChange={this.handleChange} 
            pagination={{pageSize: 8}}
          />
        {/* </Content> */}
      {/* </Layout> */}
      </div>
    );
  }
}

export default ItemsList;