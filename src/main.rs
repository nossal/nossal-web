use std::{env, path::PathBuf};
use askama_actix::Template;
use fs::NamedFile;
use log::info;
use actix_web::{get, middleware, web, App, HttpResponse, HttpServer, Responder };
use actix_web::middleware::Logger;
use env_logger::Env;
use actix_files as fs;

#[derive(Template)]
#[template(path = "hello.html")]
struct HelloTemplate<'a> {
    name: &'a str,
}

#[get("/")]
async fn hello() -> HelloTemplate<'static> {
    info!("hello world");
    HelloTemplate { name: "Rust" }
}

#[get("/healthz")]
async fn healthz() -> impl Responder {
    HttpResponse::Ok().body("Alive")
}

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    env_logger::init_from_env(Env::default().default_filter_or("info"));

    let port = env::var("PORT").unwrap_or("8080".to_string());
    let port = port.parse::<u16>().expect("Invalid port given");

    HttpServer::new(|| {
        App::new()
            .wrap(Logger::default())
            .wrap(Logger::new("%a %{User-Agent}i"))
            .wrap(middleware::DefaultHeaders::new()
                .add(("cache-control", "max-age=120, stale-while-revalidate=60, stale-if-error=3600")))
            .route("/favicon.ico", web::get().to(|| async {
                let path: PathBuf = PathBuf::from(r"resources/public/favicon.ico");
                let file = fs::NamedFile::open(path).expect("favicon not found");
                Ok::<NamedFile, std::io::Error>(file)
            }))
            .service(healthz)
            .service(fs::Files::new("/static", "resources/public/"))
            .service(hello)
    })
    .workers(4)
    .bind(("0.0.0.0", port))?
    .run()
    .await
}
