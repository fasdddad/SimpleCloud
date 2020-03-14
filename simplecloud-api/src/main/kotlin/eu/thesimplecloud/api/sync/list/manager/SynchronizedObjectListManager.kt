package eu.thesimplecloud.api.sync.list.manager

import eu.thesimplecloud.api.CloudAPI
import eu.thesimplecloud.api.network.packets.sync.list.PacketIOGetAllCachedListObjects
import eu.thesimplecloud.api.sync.list.ISynchronizedListObject
import eu.thesimplecloud.api.sync.list.ISynchronizedObjectList
import eu.thesimplecloud.clientserverapi.client.INettyClient
import java.util.concurrent.ConcurrentHashMap

class SynchronizedObjectListManager : ISynchronizedObjectListManager {

    private val nameToSynchronizedObjectList = ConcurrentHashMap<String, ISynchronizedObjectList<out ISynchronizedListObject>>()


    override fun registerSynchronizedObjectList(synchronizedObjectList: ISynchronizedObjectList<out ISynchronizedListObject>) {
        this.nameToSynchronizedObjectList[synchronizedObjectList.getIdentificationName()] = synchronizedObjectList
        if (!CloudAPI.instance.isManager()) {
            val client = CloudAPI.instance.getThisSidesCommunicationBootstrap() as INettyClient
            client.getPacketIdsSyncPromise().addResultListener {
                client.sendUnitQuery(PacketIOGetAllCachedListObjects(synchronizedObjectList.getIdentificationName()))
            }
        }
    }

    override fun getSynchronizedObjectList(name: String): ISynchronizedObjectList<ISynchronizedListObject>? = this.nameToSynchronizedObjectList[name] as ISynchronizedObjectList<ISynchronizedListObject>?

    override fun unregisterSynchronizedObjectList(name: String) {
        this.nameToSynchronizedObjectList.remove(name)
    }

}