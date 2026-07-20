# ocaml-blog

A no-database, passwordless personal blog CMS. Content is Markdown
files in a private git repo, edited offline. Pushing a commit
triggers the live server to pull and re-render — no admin login,
no redeploy, no restart.

## How it fits together

- Posts are `.md` files named `YYYY-MM-DD-slug.md`, each starting
  with a YAML front matter block (`title`, `date`, `tags`, `draft`,
  optional `slug` override).
- The server keeps all posts in memory (`Content_store`) and serves
  visitor requests from there — no per-request file parsing.
- Your git host (GitHub/GitLab) calls `POST /webhook/deploy` on
  every push. The server verifies the request is genuinely from
  your git host via an HMAC-SHA256 signature (the `X-Hub-Signature-256`
  header), then does `git fetch` + `git reset --hard origin/main`
  in its local clone, re-parses everything, and atomically swaps
  the new content in.
- There's no username/password anywhere — authentication for the
  one privileged action (redeploy) is the webhook secret you
  configure once, not a login flow.

## Required environment variables

| Variable              | Purpose                                             |
|-----------------------|------------------------------------------------------|
| `BLOG_WEBHOOK_SECRET`  | Shared secret used to verify webhook signatures      |
| `BLOG_REPO_URL`        | Remote URL of your private content repo (SSH recommended) |
| `BLOG_PORT`            | Port to listen on (default `8080`)                   |
| `BLOG_REPO_PATH`       | Local clone directory (default `./content`)          |
| `BLOG_SITE_TITLE`      | Site title shown in the header (default `My Blog`)   |

## Setting up the GitHub webhook

1. In your private content repo: **Settings → Webhooks → Add webhook**.
2. Payload URL: `https://your-domain/webhook/deploy`
3. Content type: `application/json`
4. Secret: same value as `BLOG_WEBHOOK_SECRET`
5. Trigger on: **just the push event**

GitLab uses a header called `X-Gitlab-Token` with a plain shared
secret instead of an HMAC signature — if you use GitLab instead of
GitHub, `Webhook.verify` needs a small variant for that (say the
word and I'll add it).

## Running locally

```sh
opam install dream cmarkit yaml digestif lwt
export BLOG_WEBHOOK_SECRET=devsecret
export BLOG_REPO_URL=git@github.com:you/your-content-repo.git
dune build
dune exec bin/main.exe
```

The included `content/2026-07-18-hello-world.md` is only there so
you have something to look at before wiring up a real remote repo —
once `BLOG_REPO_URL` points somewhere real, the app will clone it
into `BLOG_REPO_PATH` on first run and this sample file goes away.

## What I couldn't verify here

I wrote and reasoned through this carefully, but I don't have an
OCaml toolchain with opam access to actually `dune build` it in
this sandbox. The most likely spot for a small mismatch is the
exact `cmarkit` / `cmarkit_html` module and function names, since
that library's API has shifted across versions — a `dune build`
locally will surface anything like that immediately, and it'll be
a one-line fix if so.
