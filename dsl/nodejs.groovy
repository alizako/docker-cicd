job('NodeJS example') {
    scm {
        git('https://github.com/alizako/docker-cicd') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('DSL User')
            node / gitConfigEmail('lizzie.kor@gmail.com')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('nodejs') // this is the name of the NodeJS installation in 
                         // Manage Jenkins -> Configure Tools -> NodeJS Installations -> Name
    }
    steps {
        shell("npm install")        
    }
}

job('Second Job') {
    scm {
        //git clone:
        git('https://github.com/alizako/docker-cicd') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('DSL User')
            node / gitConfigEmail('jenkins-dsl@newtech.academy')
        }
    }
    //every 0.5 hour
    triggers {
        scm('H/5 * * * *')
    }
    //plugin named nodejs at Jenkins 
    wrappers {
        nodejs('nodejs') 
    }
    steps {
        shell("echo unit test")
        shell("echo sonar")
        shell("echo integration")
        shell("npm install")  
        dockerBuildAndPublish {
            repositoryName('alizak/docker-cicd')//Docker Hub App- repoName/imageName
            tag('${GIT_REVISION,length=9}')//build revision number
            registryCredentials('dockerhub')//looking for credentials of Docker Hub, configured on Jenkins
            forcePull(false)
            forceTag(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
}