/** 
* Color bulb notification Parent
*
*  Author: 
*    Kevin Earley 
*
*  Documentation:  Changes color led when contact is open
*
*  Changelog: V1.0
 
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
    name: "Color Bulb Notification",
    namespace: "kpejr",
    author: "Kevin Earley",
    description: "Changes color bulb when contact is open",
    category: "Convenience",
    importUrl: "https://raw.githubusercontent.com/kpejrhome/Hubitat/master/kpejr-color-bulb-notification.groovy",
	    
    iconUrl: "",
    iconX2Url: "",
    iconX3Url: "",
)


preferences {
     page name: "mainPage", title: "", install: true, uninstall: true
} 

def installed() {
    log.info "Installed with settings: ${settings}"
    initialize()
}


def updated() {
    log.info "Updated with settings: ${settings}"
    unsubscribe()
    initialize()
}

def initialize() {
    log.info "Color Bulb Notification has ${childApps.size()} child apps"
    childApps.each { child ->
        log.info "Child app: ${child.label}"
    }
}

def mainPage() {
    dynamicPage(name: "mainPage") {
                
        section("Bulb Setup"){
                input(name: "returnColor", type: "enum", title: "Which color should the bulb(s) return to after notifcation ends?", options: ["Off","Blue","Green","Grey", "Orange","Red","Purple", "White","Yellow"])
        }
        
        section() {
            app(name: "newBulb", appName: "Color Bulb Notification - Child", namespace: "kpejr", title: "<b>Add a Color Bulb Notificaiton app</b>", multiple: true)
        }		
	}
}

def areAllClosed(){

      allClosed = "YES"
    
      childApps.each { child ->
          
          bulbColor = child.areAllClosed()
          
          if(bulbColor != "YES")
          {
              allClosed = bulbColor
          }
     }
    
    return allClosed
}

