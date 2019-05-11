import React from 'react';
import { Menu, Icon } from 'antd';
import { Link } from 'react-router-dom';

const SubMenu = Menu.SubMenu;

const HeaderMenu = () => {
    return (
      <Menu mode="horizontal">
        <SubMenu title={<span className="submenu-title-wrapper"><Icon type="setting" theme="twoTone" />Navigation</span>}>
            
            <Menu.Item key="Dashboard">
                <Link to='/'><Icon type="home" theme="twoTone" />Dashboard</Link>
            </Menu.Item>
            
            <Menu.Item key="Customers">
                <Link to='/customers'><Icon type="idcard" theme="twoTone" />Customers</Link>
            </Menu.Item>

            <Menu.Item key="Items">
                <Link to='/items'><Icon type="shopping" theme="twoTone" />Items</Link>
            </Menu.Item>
          
        </SubMenu>
      </Menu>
    );
}

export default HeaderMenu;