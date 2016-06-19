# GoogleplusTest
login with google plus
STEP1)Create new project on google developers console and enable google sign in api 
     google developers console :
	 https://console.developers.google.com
	 or
	
	 
STEP2)Download googel-services.json file and configure it in your project.
		-if json file not getting,create here
			 https://developers.google.com/mobile/add?platform=android&cntapi=signin&cnturl=https:%2F%2Fdevelopers.google.com%2Fidentity%2Fsign-in%2Fandroid%2Fsign-in%3Fconfigured%3Dtrue&cntlbl=Continue%20Adding%20Sign-In
			 
		-enter project name and also package name and provide SHA 1 cERTIFICATE
	
		-for generating sha 1 in windows (if asked to enter password,default password is android )
		 >cd..
		 >cd..
		 >cd C:\Program Files\Java\jdk1.8.0_65\bin
		 >keytool -list -v -keystore "%USERPROFILE%\.android\debug.keystore" -alias androiddebugkey -storepass android -keypass android
		 >you will get 32 bit sha1 key,copy it in developers console sha1 to generate google services.json 
		 Certificate fingerprints:
         MD5:  XXXXXXXXXXXXXXXXXXXXX
		 
		 -now enable google sign in 
		 
		 -click on generate configuration file and download google-services.json 
		 
STEP3)copy downloaded googel-services.json file from your download folder and paste inside your app directory of project.
			-Go to YourProject>>app>> and paste your downloaded file here
			
STEP4)Add Playservices dependency in build.gradle.
			-PROJECT LEVEL BUILD.GRADLE,ADD DEPENDENCY : classpath 'com.google.gms:google-services:2.0.0-alpha6'
			-APP LEVEL BUILD.GRADLE,ADD FOLLOWING
			PLUGIN : apply plugin: 'com.google.gms.google-services'
			GRADLE :compile 'com.google.android.gms:play-services-auth:9.0.2'
					compile 'com.google.android.gms:play-services-plus:9.0.2'
			
STEP5)Add INTERNET permission to manifest file 	
