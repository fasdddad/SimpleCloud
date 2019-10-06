package eu.thesimplecloud.launcher.setups

import eu.thesimplecloud.launcher.startup.Launcher
import eu.thesimplecloud.launcher.application.CloudApplicationStarter
import eu.thesimplecloud.launcher.application.CloudApplicationType
import eu.thesimplecloud.launcher.console.setup.ISetup
import eu.thesimplecloud.launcher.console.setup.ISetupQuestion

class StartSetup : ISetup {

    private var cloudApplicationType: CloudApplicationType? = null

    override fun questions(): List<ISetupQuestion> {
        val list = ArrayList<ISetupQuestion>()
        list.add(object : ISetupQuestion {
            override fun questionName(): String = "Do you want do start the Manager or the Wrapper"

            override fun onResponseReceived(answer: String): Boolean {
                if (answer.equals("manager", true) || answer.equals("wrapper", true)) {
                    cloudApplicationType = CloudApplicationType.valueOf(answer)
                    return cloudApplicationType != null
                }
                return false
            }

        })

        return list
    }

    override fun onFinish() {
        ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor()
        Launcher.instance.logger.info("Starting ${cloudApplicationType?.name}...")
        CloudApplicationStarter().startCloudApplication(cloudApplicationType!!)
    }
}