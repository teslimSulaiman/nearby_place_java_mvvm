package com.example.owner.myapplication;

import android.arch.lifecycle.MutableLiveData;
import android.os.Looper;

import com.example.owner.myapplication.api.ApiResponse;
import com.example.owner.myapplication.home.Repository;
import com.example.owner.myapplication.mapper.NearbyPlaceInfoMapper;
import com.example.owner.myapplication.model.NearbyPlace;
import com.example.owner.myapplication.model.NearbyPlaceInfo;
import com.example.owner.myapplication.viewModel.NearbyPlaceViewModel;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Observable.class, AndroidSchedulers.class, Looper.class, NearbyPlace.class})
public class NearbyPlaceViewModelTest {

    public static final String TEST_ERROR_MESSAGE = "error_message";


    @Mock
    private Repository repository;
    @Mock
    private NearbyPlaceInfo nearbyPlaceInfo;
    @Mock
    private NearbyPlaceInfoMapper nearbyPlaceInfoMapper;
    @Mock
    private MutableLiveData<ApiResponse> responseLiveData;

    @Mock
    private List<NearbyPlace> mObservable = new ArrayList<>();

    private NearbyPlaceViewModel nearbyPlaceViewModel;

    @Captor
    private ArgumentCaptor<rx.Subscriber<NearbyPlace>> captor;

    private final RxJavaSchedulersHook mRxJavaSchedulersHook = new RxJavaSchedulersHook() {
        @Override
        public Scheduler getIOScheduler() {
            return rx.schedulers.Schedulers.immediate();
        }

        @Override
        public rx.Scheduler getNewThreadScheduler() {
            return rx.schedulers.Schedulers.immediate();
        }
    };

    @Before
    public void setUp() throws Exception {
        initMocks(this);

    }


}
