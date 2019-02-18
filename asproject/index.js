import React, { Component } from 'react';
import { AppRegistry, Text } from 'react-native';

class HelloWorldApp extends Component {
    _toast(){
        NativeModules.CallModule111.callTost('toastlph')
    }
  render() {
    return (
      <Text>Hello world111111111!</Text>

    );
  }
}

// 注意，这里用引号括起来的'HelloWorldApp'必须和你init创建的项目名一致
AppRegistry.registerComponent('asproject', () => HelloWorldApp);