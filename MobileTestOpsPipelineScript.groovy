node {
    def mvnHome
    stage('Checkout') {
        checkout([$class           : 'GitSCM',
                  branches         : [[name: 'branchName']],
                  extensions       : [],
                  userRemoteConfigs: [
                          [credentialsId: 'xxxxxx-xxxxxx-xxxx-xxxx-xxxxxxxxxxx',
                           url          : 'https://github.com/xxxxxx/xxxxx.git']]])
    }
    stage('Build') {
        sh './gradlew - info assembleDebug'
    }
    stage('Application Result') {
        archiveArtifacts artifacts: '**/*.apk', followSymlinks: false, onlyIfSuccessful: true
    }
    stage('Upload to Cloud Device') {
        //Upload APK to Browser stack or Kobiton cloud device
    }
    stage('Run Test Suite') {
        executeKatalon executeArgs: './katalonc -noSplash -runMode=console -projectPath="xxxxx" ' +
                '-retry=0 -testSuitePath="Test Suites/Dev" -kobitonDeviceId="xxxxxx" -executionProfile="DEV profile" ' +
                '-browserType="Kobiton Device" -apiKey="xxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxx" ' +
                '- config -proxy.system.applyToDesiredCapabilities=true ' +
                '-kobiton.authentication.serverUrl="https://api.kobiton.com/wd/hub" ' +
                '-kobiton.authentication.username="xxxxxxx" ' +
                '-kobiton.authentication.apiKey="xxxxxx-xxxx-xxxx-xxxx-xxxxxxxxx"', location: '', version: '8.0.1', x11Display: ' ', xvfbConfiguration: ' '
    }
}