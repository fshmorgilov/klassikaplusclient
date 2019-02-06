package com.themaker.fshmo.klassikaplus.dagger;

import android.app.Application;
import com.themaker.fshmo.klassikaplus.dagger.module.ApplicationModule;
import com.themaker.fshmo.klassikaplus.dagger.module.DataModule;
import com.themaker.fshmo.klassikaplus.data.persistence.AppDatabase;
import com.themaker.fshmo.klassikaplus.data.repositories.CatalogRepository;
import com.themaker.fshmo.klassikaplus.data.web.catalog.CatalogApi;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        DataModule.class})
public interface AppComponent {

    void inject(AppDatabase database);

    void inject(CatalogRepository repository);

    void inject(CatalogApi api);
//    Application providesApplication();
//
//    AppDatabase getDb();
//
//    CatalogRepository getCatalogRepository();

}
