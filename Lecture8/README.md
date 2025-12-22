##  Network Technologies
1. Find host of VM with ifconfig
2. Set Vm as a server ncat -l -p 4433
3. Test connection from mac to VM with sending file ncat <VM IP> 4433 < tcp_test![MacSend.png](images/MacSend.png)![VmFile.png](images/VmFile.png)
4. Test TCP connection with ncat -l -p 4433 on VM and ncat <VM IP> 4433 on Mac ![testTCPconnection.png](images/testTCPconnection.png)
5. Test UDP connection with ncat -u -l -p 4433 on VM and ncat -u <VM IP> 4433 on Mac ![testUDPconnection.png](images/testUDPconnection.png)
6. Use -v flag to see more info about TCP connection to the listener  and random port![TCPncatvflag.png](images/TCPncatvflag.png)
7. Use -v flag to see more info about UDP connection to the listener  and random port![UDPncatvflag.png](images/UDPncatvflag.png)
8. We can see that TCP connection through ncat show an error when we try to connect to a closed port, while UDP connection does not show any error when connecting to a closed port. Message send from client but didn't show on server side
9. Enable firewall on VM, request from client machine failed with timeout ![TCPwithEnableFireWall.png](images/TCPwithEnableFireWall.png)

