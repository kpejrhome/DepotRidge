/** 
* Group Switch - Child
*
*  Author: 
*    Kevin Earley 
*
*  Documentation:  Use a virtual or real switch to group other switches
*
*  Changelog:

*
*  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License. You may obtain a copy of the License at:
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
*  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
*  for the specific language governing permissions and limitations under the License.
*
*/
definition(
    name: "Group Switch - Child",
    namespace: "kpejr",
    author: "Kevin Earley",
    description: "Use a virtual or real switch to group other switches",
    category: "Convenience",
    parent: "kpejr:Group Switch",
    importUrl: "https://raw.githubusercontent.com/kpejrhome/Hubitat/master/kpejr-group-switch-child.groovy",
    
    iconUrl: "",
    iconX2Url: "",
    iconX3Url: "" )

preferences{ 
    page( name: "childSetup")
}

def childSetup(){ 
    dynamicPage(name: "childSetup", title: "Group Switch - Child", nextPage: null, install: true, uninstall: true, refreshInterval: 0) {
        
        section("Device Section"){
            
            label title: "Enter a name for this app", required: true
            
             input "triggerSwitch", "capability.switch", title: "Which switch(s) do you want to use to activate other switches?", multiple: true, required: true
             input "targetOnSwitch", "capability.switch", title: "Which switch(s) do you want to turn on?", multiple: true, required: false
             input "targetOffSwitch", "capability.switch", title: "Which switch(s) do you want to turn of?", multiple: true, required: false
        }
    }
}

def installed() {
    log.info "Installed application"
    unsubscribe()
    unschedule()
    initialize()
}

def updated() {
    log.info "Updated application"
    unsubscribe()
    unschedule()
    initialize()
}

def initialize(){
    log.info("Initializing with settings: ${settings}")
    
    subscribe(settings.triggerSwitch, "switch", switchHandler)
}
       

def switchHandler( evt ){
    log.info "${evt.name} was reported ${evt.value}"
    
    if(evt.value == "on") {
      for(device in settings.targetOnSwitch){
        log.info "Group Switch turing on ${device.getLabel()}"
        device.on()
      }
    }
    else
    {
      for(device in settings.targetOffSwitch){
        log.info "Group Switch turing off ${device.getLabel()}"
        device.off()
      }
    }
}