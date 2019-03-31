package flux

import io.reactivex.Observable
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class Store : KoinComponent {

    private val dispatcher by inject<Dispatcher>()

    protected val observable: Observable<Action> = dispatcher

}