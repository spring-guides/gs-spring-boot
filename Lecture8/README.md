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

##  DHCP server 
1. Set up DHCP server on VM using dnsmasq
2. Configure dnsmasq by adding  /etc/dnsmasq.d new config file to set test-dnsmasq1.conf the vm listen-address and ports
3. Validate dnsmasq config with sudo dnsmasq --test
4. Restart dnsmasq service with sudo systemctl restart dnsmasq
5. Check dnsmasq service status with sudo systemctl status dnsmasq --no-pager
6. Firewall inactive on VM
7. Check if DHCP server from my local machine with  dig -r 4433 @my.test.vm.ip vm.lab ![DHCPresponse.png](images/DHCPresponse.png)
8. Dig google which is not in dnsmasq config 
9. We see Answers section 6 on google and 1 on vm.lab which means that when we guery vm.lab the request is handled by dnsmasq server (address row in dnsmasq config)
while google request forwards to upstream DNS (1.1.1.1/8.8.8.8), because it's not in dnsmasq config file. Then this aswer is cached by dnsmasq for future requests. And we can se that response time 25ms![CachedResponse.png](images/CachedResponse.png)
10. 
