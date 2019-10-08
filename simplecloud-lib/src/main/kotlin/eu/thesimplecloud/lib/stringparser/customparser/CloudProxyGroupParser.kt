package eu.thesimplecloud.lib.stringparser.customparser

import eu.thesimplecloud.lib.CloudLib
import eu.thesimplecloud.lib.servicegroup.grouptype.ICloudProxyGroup
import eu.thesimplecloud.lib.servicegroup.grouptype.ICloudServerGroup
import eu.thesimplecloud.lib.stringparser.ICustomTypeParser
import kotlin.reflect.KClass

class CloudProxyGroupParser : ICustomTypeParser<ICloudProxyGroup> {

    override fun allowedTypes(): List<Class<out ICloudProxyGroup>> = listOf(ICloudProxyGroup::class.java)

    override fun parse(string: String): ICloudProxyGroup? = CloudLib.instance.getCloudServiceGroupManager().getProxyGroup(string)
}