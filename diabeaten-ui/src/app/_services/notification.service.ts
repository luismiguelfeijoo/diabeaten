import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { environment } from 'src/environments/environment';
import { AuthenticationService } from './authentication.service';
import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { User } from '../_models';

@Injectable()
export class NotificationService {
  webSocketEndPoint = `${environment.socketUrl}/ws`;
  topic: string = `${
    this.authenticationService.isAdmin(this.authenticationService.userValue)
      ? '/topic/admin'
      : '/topic/user-' + this.authenticationService.userValue.id
  }`;
  // topic = '/topic/notification';
  stompClient: any;

  constructor(
    private authenticationService: AuthenticationService,
    private toastr: ToastrService
  ) {}

  _connect() {
    console.log(
      'Initialize WebSocket Connection',
      this.authenticationService.isAdmin(this.authenticationService.userValue)
    );
    let ws = new SockJS(this.webSocketEndPoint);
    this.stompClient = Stomp.over(ws);
    const _this = this;
    _this.stompClient.connect(
      {},
      function (frame) {
        _this.stompClient.subscribe(_this.topic, function (message) {
          _this.onMessageReceived(message);
        });
        //_this.stompClient.reconnect_delay = 2000;
      },
      this.errorCallBack
    );
  }

  _disconnect() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
    }
    console.log('Disconnected');
  }

  // on error, schedule a reconnection attempt
  errorCallBack(error) {
    console.log('errorCallBack -> ' + error);
    setTimeout(() => {
      this._connect();
    }, 5000);
  }

  /**
   * Send message to sever via web socket
   * @param {*} message
   */
  _send(message: any, url: string) {
    console.log('calling logout api via web socket');
    console.log(this.authenticationService.userValue);
    this.stompClient.send(
      url,
      { senderId: this.authenticationService.userValue.id },
      message
    );
  }

  onMessageReceived(message) {
    console.log('Message Recieved from Server :: ' + message);
    const notification = JSON.parse(message.body);
    this.toastr.success(`${notification.message}`, '', {
      timeOut: 2000,
      enableHtml: true,
      toastClass: 'alert alert-success alert-with-icon',
      positionClass: 'toast-top-right',
    });
  }
}
