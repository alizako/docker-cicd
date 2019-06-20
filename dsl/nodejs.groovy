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
        git('https://github.com/alizako/docker-cicd') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('DSL User')
            node / gitConfigEmail('jenkins-dsl@newtech.academy')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('nodejs-new') 
    }
    steps {
        shell(echo "unit test")
        shell(echo "sonar")
        shell(echo "integration")
        shell("npm install")  
        dockerBuildAndPublish {
            repositoryName('alizako/docker-cicd')
            tag('${GIT_REVISION,length=9}')
            registryCredentials('dockerhub')
            forcePull(false)
            forceTag(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
}