import React, { Component } from 'react';
import { AppRegistry, Text,NativeModules} from 'react-native';

class HelloWorldApp extends Component {
  _toast(){
    NativeModules.CallModule.callTost('toastlph111',(success)=>{alert(success)})
}
  render() {
    return (
      <Text  onPress={this._toast} >Hello 22222222221!</Text>
    );
  }
}

// 注意，这里用引号括起来的'HelloWorldApp'必须和你init创建的项目名一致
AppRegistry.registerComponent('asproject', () => HelloWorldApp);