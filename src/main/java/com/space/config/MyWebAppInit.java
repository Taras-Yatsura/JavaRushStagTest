package com.space.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

//Вся логика работы Spring MVC построена вокруг DispatcherServlet, который принимает и обрабатывает все HTTP-запросы
//(из UI) и ответы на них.
//Ниже приведена последовательность событий, соответствующая входящему HTTP-запросу:
//
//После получения HTTP-запроса DispatcherServlet обращается к интерфейсу HandlerMapping, который определяет,
//какой Контроллер должен быть вызван, после чего, отправляет запрос в нужный Контроллер.

//Контроллер принимает запрос и вызывает соответствующий служебный метод, основанный на GET или POST.
//Вызванный метод определяет данные Модели, основанные на определённой бизнес-логике и возвращает в DispatcherServlet
//имя Вида (View).
//При помощи интерфейса ViewResolver DispatcherServlet определяет, какой Вид нужно использовать на основании
//полученного имени.
//После того, как Вид (View) создан, DispatcherServlet отправляет данные Модели в виде атрибутов в Вид, который
//в конечном итоге отображается в браузере.
//
//Все вышеупомянутые компоненты, а именно, HandlerMapping, Controller и ViewResolver, являются частями интерфейса
//WebApplicationContext extends ApplicationContext, с некоторыми дополнительными особенностями, необходимыми для создания web-приложений.
public class MyWebAppInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.setInitParameter("spring.profiles.active", "prod");
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}