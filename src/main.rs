use actix_files as fs;
use actix_web::middleware::Logger;
use actix_web::{get, middleware, web, App, HttpResponse, HttpServer, Responder};
use askama_actix::Template;
use env_logger::Env;
use fs::NamedFile;
use log::info;
use pulldown_cmark::{html, Event};
use pulldown_cmark::Tag;
use pulldown_cmark::{HeadingLevel, Parser};
use std::fs::File;
use std::io::{self, BufRead, BufReader};
use std::os::linux::fs::MetadataExt;
use std::{env, path::PathBuf};

#[derive(Template)]
#[template(path = "hello.html")]
struct HelloTemplate<'a> {
    name: &'a str,
}

#[derive(Template)]
#[template(path = "content.html")]
struct ContentTemplate {
    name: String,
    content: String
}

#[derive(Template)]
#[template(path = "contents.html")]
struct ContentsTemplate<'a> {
    name: &'a str,
    articles:  Vec<Article>
}

#[derive(Debug)]
struct Article {
    title: String,
    name: Box<str>,
    path: String,
    date_modified: i64,
    date_created: i64
}

fn read_first_10_lines(file_path: &PathBuf) -> io::Result<String> {
    let file = File::open(file_path)?;
    let reader = BufReader::new(file);

    let mut result = String::new();
    for line in reader.lines().take(10) {
        let line = line?;
        result.push_str(&line);
        result.push('\n');
    }

    Ok(result)
}

fn get_title(file_path: &PathBuf) -> Option<String> {
    let markdown = read_first_10_lines(file_path).expect("dasdasd");
    let mut title: Option<String> = None;
    let parser = Parser::new(&markdown);
    // println!("{}", markdown);

    let mut found = false;
    for event in parser {
        match event {
            Event::Start(Tag::Heading { level: HeadingLevel::H1, .. }) => { found = true; }
            Event::Text(text) if found => {
                title = Some(text.to_string());
                break;
            }
            _ => {}
        }
    }

    title
}

fn get_article_from(name: &str) -> io::Result<String> {
    let mut path = String::from("./resources/contents/");
    path.push_str(&name);

    let markdown_content = std::fs::read_to_string(path)?;
      // Create a parser
    let parser = Parser::new(&markdown_content);

    // Generate HTML
    let mut html_output = String::new();
    html::push_html(&mut html_output, parser);

    Ok(html_output)
}

fn get_articles_from() -> Vec<Article> {
    let mut articles: Vec<Article> = vec![];

    for entry in std::fs::read_dir("./resources/contents").expect("error") {
        let entry = entry.expect("error entry");
        let path = entry.path();
        let meta = entry.metadata().unwrap();

        let date_modified = meta.to_owned().st_mtime();
        let date_created = meta.to_owned().st_ctime();

        let title = match get_title(&path) {
            Some(expr) => expr,
            None => "-----".into(),
        };

        // let extension = match path.extension() {
        //     Some(expr) => expr.to_str(),
        //     None => "--".into(),
        // };

        let name = match path.file_name() {
            Some(expr) => expr.to_str(),
            None => "--".into(),
        };

        let a = Article {
            name: name.unwrap().into(),
            title,
            path: path.into_os_string().into_string().unwrap(),
            date_modified,
            date_created
        };
        articles.push(a);
    }

    articles
}

#[get("/content/{name}")]
async fn content(path: web::Path<String>) -> ContentTemplate {
    let name = path.into_inner();

    let content = get_article_from(&name).expect("no article");

    ContentTemplate { name, content }
}

#[get("/contents")]
async fn contents() -> ContentsTemplate<'static> {
    let articles = get_articles_from();
    for art in &articles {
        info!("file: {:?} ", art);
    }

    ContentsTemplate { name: "Rust", articles }
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
            .wrap(middleware::Compress::default())
            .wrap(middleware::DefaultHeaders::new().add((
                "cache-control",
                "max-age=120, stale-while-revalidate=60, stale-if-error=3600",
            )))
            .route(
                "/favicon.ico",
                web::get().to(|| async {
                    let path: PathBuf = PathBuf::from(r"resources/public/favicon.ico");
                    let file = fs::NamedFile::open(path).expect("favicon not found");
                    Ok::<NamedFile, std::io::Error>(file)
                }),
            )
            .service(contents)
            .service(content)
            .service(healthz)
            .service(fs::Files::new("/static", "resources/public/"))
            .service(hello)
    })
    .workers(4)
    .bind(("0.0.0.0", port))?
    .run()
    .await
}
