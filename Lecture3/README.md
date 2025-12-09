## Jenkins freestyle job
1. Create a new Jenkins freestyle job named "Simple Freestyle Job".![Alt text](Lecture3/images/Screenshot 2025-12-09 at 15.47.23.png)
2. Add my git repo in Source Code Management ![Alt text](Lecture3/images/Screenshot 2025-12-09 at 15.48.30.png)
3. Setup some environment variables for test  ![Alt text](Lecture3/images/Screenshot 2025-12-09 at 15.49.02.png)
4. Configured Build steps:
   - Install maven plugins
   - Add goal "clean install"  
   - ![Alt text](Lecture3/images/Screenshot 2025-12-09 at 15.49.31.png)
5. Configure Post-build actions:
   - Add "Archive the artifacts" and specify the path to the build artifacts `**/*.jar` to archive all .jar files 
   - ![Alt text](Lecture3/images/Screenshot 2025-12-09 at 15.49.48.png)

## Jenkins pipline
1. Create a new Jenkins pipeline job named "Simple First Pipeline".
2. Add my git repo in Source Code Management
3. Setup pipline defenition for all git branches and also added my test branch with Jenkins file ![Alt text](Lecture3/images/Screenshot 2025-12-09 at 15.44.53.png)
4. Create separate branch with Jenkinsfile
5. Configure Jenkinsfile with:
   - Add Maven tolls to build project with mvn clean install
   - Add telegram secrets in Jenkins credentials  ![Alt text](Lecture3/images/Screenshot 2025-12-09 at 15.46.10.png)
6. Added 'Pre-build', 'Build', 'Post-build' stage
7. Pre-build stage:
   - Added telegram notification for start build
8. Build stage:
   - Added mvn clean install with path to pom.xml file
9. Post-build stage:
   - Added 'Archive the artifacts' and specify the path to the build artifacts `**/*.
10. Post:
   - Added telegram notification for build success or failure ![Alt text](Lecture3/images/Screenshot 2025-12-09 at 15.33.00.png)
