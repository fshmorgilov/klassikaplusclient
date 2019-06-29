package com.themaker.fshmo.klassikaplus.dagger;

import android.app.Application;
import com.themaker.fshmo.klassikaplus.dagger.module.ApplicationModule;
import com.themaker.fshmo.klassikaplus.dagger.module.DataModule;
import com.themaker.fshmo.klassikaplus.data.persistence.AppDatabase;
import com.themaker.fshmo.klassikaplus.data.preferences.Preferences;
import com.themaker.fshmo.klassikaplus.data.repositories.CatalogRepository;
import com.themaker.fshmo.klassikaplus.data.web.catalog.CatalogApi;
import com.themaker.fshmo.klassikaplus.presentation.decoration.StarDrawer;
import com.themaker.fshmo.klassikaplus.presentation.catalog.CatalogPresenter;
import com.themaker.fshmo.klassikaplus.presentation.novelties.NoveltyPresenter;
import com.themaker.fshmo.klassikaplus.presentation.novelties.NoveltySwipeController;
import com.themaker.fshmo.klassikaplus.presentation.root.MainActivity;
import com.themaker.fshmo.klassikaplus.service.RevisionRequestService;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        DataModule.class})
public interface AppComponent {

    void inject(MainActivity activity);

    void inject(RevisionRequestService service);

    void inject(NoveltyPresenter presenter);

    void inject(Preferences preferences);

    void inject(CatalogRepository repository);

    void inject(CatalogPresenter presenter);

    void inject(NoveltySwipeController controller);

    void inject(StarDrawer drawer);

}
