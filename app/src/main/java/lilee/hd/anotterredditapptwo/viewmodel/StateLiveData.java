package lilee.hd.anotterredditapptwo.viewmodel;

import androidx.lifecycle.MutableLiveData;

import lilee.hd.anotterredditapptwo.model.StateData;

public class StateLiveData<T> extends MutableLiveData<StateData<T>> {
    /**
     * Use this to put the Data on a LOADING Status
     */
    public void postLoading() {
        postValue(new StateData<T>().loading());
    }

    /**
     * Use this to put the Data on a ERROR DataStatus
     * @param throwable the error to be handled
     */
    public void postError(Throwable throwable) {
        postValue(new StateData<T>().error(throwable));
    }

    /**
     * Use this to put the Data on a SUCCESS DataStatus
     * @param data
     */
    public void postSuccess(T data) {
        postValue(new StateData<T>().success(data));
    }

    /**
     * Use this to put the Data on a COMPLETE DataStatus
     */
    public void postComplete() {
        postValue(new StateData<T>().complete());
    }
}
