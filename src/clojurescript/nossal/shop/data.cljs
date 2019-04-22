(ns nossal.shop.data
  (:require [clojure.spec.alpha :as s]))


(def products-db
  [{:code 1
    :name "TS100 Soldering Iron"
    :price 89.87
    :descr ""
    :link "https://www.banggood.com/MINI-TS100-Digital-OLED-Programmable-Interface-DC-5525-Soldering-Iron-Station-Built-in-STM32-Chip-p-984214.html"
    :partner "banggood"
    :images ["https://img.staticbg.com/thumb/large/oaupload/banggood/images/88/63/264c0b48-0560-46b2-9213-c357c90b5815.jpg"
             "https://img.staticbg.com/thumb/view/upload/2015/12/552/38.jpg"
             "https://img.staticbg.com/thumb/view/upload/2015/12/SKU252598/15.jpg"
             "https://img.staticbg.com/thumb/view/oaupload/banggood/images/7B/44/6cf50167-7cd1-480c-9848-af37daffcfce.jpg"]}
   {:code 15
    :name "Roborock S50 Smart Robot"
    :price 2800.89
    :descr ""
    :link "https://www.banggood.com/MINI-TS100-Digital-OLED-Programmable-Interface-DC-5525-Soldering-Iron-Station-Built-in-STM32-Chip-p-984214.html"
    :partner "banggood"
    :images ["https://img.staticbg.com/thumb/large/oaupload/banggood/images/7F/9A/f6bcda05-e8d8-46a0-a051-9b02dfd6ed96.gif"]}
   {:code 16
    :name "Stainless Steel Mini Pocket Folding Knife"
    :price 37.69
    :descr ""
    :link "https://www.banggood.com/MINI-TS100-Digital-OLED-Programmable-Interface-DC-5525-Soldering-Iron-Station-Built-in-STM32-Chip-p-984214.html"
    :partner "banggood"
    :images ["https://img.staticbg.com/thumb/large/oaupload/banggood/images/4D/6A/311f446a-bca0-4d12-853d-1b95d517d019.jpg.webp"]}
   {:code 17
    :name "Hantek DSO5072P"
    :price 850.89
    :descr ""
    :link "https://www.banggood.com/MINI-TS100-Digital-OLED-Programmable-Interface-DC-5525-Soldering-Iron-Station-Built-in-STM32-Chip-p-984214.html"
    :partner "banggood"
    :images ["https://img.staticbg.com/thumb/large/oaupload/banggood/images/18/97/55a957e6-ad5d-411c-a20b-2088279c13f9.jpg.webp"]}])

