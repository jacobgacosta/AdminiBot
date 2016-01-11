package dagger;

import javax.inject.Singleton;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

     AdminiBotComponent plus(AdminiBotModule module);

}
