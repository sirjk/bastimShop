 import React, {FunctionComponent, useState} from 'react'
 import { Table, Radio, Divider } from 'antd';
 import {EditOutlined, PlusOutlined} from "@ant-design/icons";
 import {Link} from "react-router-dom";
// import {Simulate} from "react-dom/test-utils";
// import change = Simulate.change;
//
 interface Props{
 }

 function textToURL(text: string):string{
     text = text.toLowerCase();
    for(let char of text){
        if(char === ' '){
            let index:number = text.indexOf(char);
            let newChar:string = '-';
            text = text.substring(0,index) +newChar+text.substring(index+1);
        }
    }
     return text;
 }

const columns = [
    {
        title: 'Name',
        dataIndex: 'name',
        render: (text: string) => <Link to={"/Admin-"+textToURL(text)}>{text}</Link>,
    },
    {
        title: '',
        dataIndex: 'add',
        render: (text: string) => <Link to={"/Admin-"+textToURL(text)}><PlusOutlined/>{text}</Link>,
    },
    {
        title: '',
        dataIndex: 'change',
        render: (text: string) => <Link to={"/Admin-"+textToURL(text)}><EditOutlined />{text}</Link>,
    },
];

interface DataType {
    key: React.Key;
    name: string;
    add: string;
    change: string;
 }
const data: DataType[] = [
    {
        key: '1',
        name: 'Categories',
        add: 'Add',
        change: 'Change',
    },
    {
        key: '2',
        name: 'Orders',
        add: 'Add',
        change: 'Change',
    },
    {
        key: '3',
        name: 'Permissions',
        add: ' Add',
        change: ' Change',
    },
    {
        key: '4',
        name: 'Products',
        add: ' Add',
        change: ' Change',
    },
    {
        key: '5',
        name: 'Role Permissions',
        add: ' Add',
        change: ' Change',
    },
    {
        key: '6',
        name: 'Roles',
        add: ' Add',
        change: ' Change',
    },
    {
        key: '7',
        name: 'User Roles',
        add: ' Add',
        change: ' Change',
    },
    {
        key: '8',
        name: 'Users',
        add: ' Add',
        change: ' Change',
    },
];




 export const ControlMainPage: FunctionComponent<Props>=(props: Props)=>{

     return (
         <div>
             <Table
                 style={{width:"50%"}}
                 columns={columns}
                 dataSource={data}
             />
         </div>
     );
 }

