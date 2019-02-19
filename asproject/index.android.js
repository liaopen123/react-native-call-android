import React from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
  ToastAndroid,
  NativeModules,
  DeviceEventEmitter
} from 'react-native';

export default class HelloWorldApp extends React.Component {

    constructor(props){
        super(props);
        this.state={
             text1:'ToastForAndroid',
             text2:'testAndroidCallbackMethod',
             text3:'textAndroidPromiseMethod',
             text4:'DeviceEventEmitter',
             text5:'getValue',
             data:'no_data',
        }
    }
    //此处代码  一进来就会被调用  所以会添加监听   和 执行getDataFromIntent方法
    componentWillMount1() {//部件将会被挂载
        DeviceEventEmitter.addListener('EventName', function  (msg) {
            console.log(msg);
            let rest=NativeModules.CallModule.MESSAGE;
            ToastAndroid.show("DeviceEventEmitter收到消息:" + "\n" + rest, ToastAndroid.SHORT)
        });
        NativeModules.CallModule.getDataFromIntent((result)=>{
            this.setState({data:result});
        });
    }

   render() {
     return (
          <View style={styles.container}>
           <Text>{this.state.data}</Text>
           <TouchableOpacity onPress={this._onPressButton.bind(this)}>
                 <Text style={styles.hello}>{this.state.text1}</Text>
           </TouchableOpacity>
           <TouchableOpacity onPress={this._onPressButton2.bind(this)}>
                 <Text style={styles.hello}>{this.state.text2}</Text>
            </TouchableOpacity>
           <TouchableOpacity onPress={this._onPressButton3.bind(this)}>
                 <Text style={styles.hello}>{this.state.text3}</Text>
           </TouchableOpacity>
           <TouchableOpacity onPress={this._onPressButton4.bind(this)}>
                 <Text style={styles.hello}>{this.state.text4}</Text>
           </TouchableOpacity>
           <TouchableOpacity onPress={this._onPressButton5.bind(this)}>
                 <Text style={styles.hello}>{this.state.text5}</Text>
           </TouchableOpacity>
          </View>
        )
   }

    _onPressButton(){
        NativeModules.CallModule.show(1000);
   }

    _onPressButton2(){
        NativeModules.CallModule.testAndroidCallbackMethod("HelloJack",(result)=>{
           this.setState({text:result});
       });
    }

    _onPressButton3(){
       NativeModules.CallModule.textAndroidPromiseMethod("abcx").then((result)=>{
                 this.setState({text3:result});
             }).catch((error)=>{
                 this.setState({text:'error'});
             })
    }

    _onPressButton4(){
        NativeModules.CallModule.sendEvent();
    }

    _onPressButton5(){
         ToastAndroid.show(NativeModules.CallModule.MESSAGE, ToastAndroid.SHORT)
    }

}

   var styles = StyleSheet.create({
     container: {
        flex: 1,
        justifyContent: 'center',
        flexDirection: 'column',
      },
      hello: {
        fontSize: 20,
        textAlign: 'center',
        margin: 10,
      },
    });

// 注意，这里用引号括起来的'HelloWorldApp'必须和你init创建的项目名一致
AppRegistry.registerComponent('asproject', () => HelloWorldApp);