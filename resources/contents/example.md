# code #
```bash
gpg -d this_file.gpg \
| gpg -e \
    -r "Some Name" \
    -r "Another Name" \
    -o this_file.gpg.new \
    --batch --yes

mv -f this_file.gpg.new this_file.gpg
```
