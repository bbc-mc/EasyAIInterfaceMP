MOD: mod_EasyAIInterface
----

----
    MOD name     : mod_EasyAIInterface
    Author       : bbc_mc (bbc-mc on github)
    status       : alpha + still develop
    publish date : (2012/10/19 alpha)
                   2012/11/11 alpha2
----
** 現在開発中のため、以下の内容には開発中の情報が含まれ、不正確な可能性があります。**

## 概要

 - ゲーム [Minectaft](http://www.mojang.com/) に以下の機能を追加する MOD です。

 - 開発中の mod を MC1.4.2 へ移植したものです。

## 機能

 - AI を簡単に構築する仕組みを追加する
 - AI 構築に利用する Item "チップ"を追加する

## 動作環境・前提

以下の環境が必要です。

  + MC 1.4.2
  + Minecraft Forge (開発環境 6.0.1.341)

## 参考実装
  + 以下の条件を満たすEntity であれば、本仕組みを実装可能です。
    + EntityLiving である（もしくはそれを継承している）
    + AI チップを配置できる inventory を持つ
    + 定期的に EAI_Manager の実行関数をキックできる

  + AI チップ動作検証用に、インベントリを持つ無機能 Mob を作成しました
    [ソースコード][github_EAIMobSampleMP]
    [バイナリ @ 非公式フォーラム トピック][topic]

## 使用 ID

 - 開発に伴う AI チップの追加で増加する可能性大です。
 - Config から変更可能です

+ アイテムID
  + 29001 - 29007
  + 29101 - 29104
  + 29201 - 29208
  + 29301 - 29304
  + 29401
+ ブロックID
  + なし

## 導入方法

 - mod_EasyAIInterfaceMP.zip を、あなたの mods フォルダへ投入

## ライセンス

  - ソースコードのライセンスは、[MIT license][MIT] と [GPL license][GPL] の Dual License とします

## 免責

  - ご利用は自己責任でお願いします

## 謝辞

  - 拡張性の高い AI プラットフォーム である [YoujoMOD] を公開されている作者様に感謝します

## 公開先

  - Minecraft 日本非公式ユーザーフォーラム
    - <http://forum.minecraftuser.jp/viewtopic.php?f=13&t=6134&p=49598#p49598>
  - github (ソースコード)
    - MC1.2.5
      - EasyAIInterface
        <https://github.com/bbc-mc/EasyAIInterface>
      - YoujoAI_EasyAIInterface
        <https://github.com/bbc-mc/YoujoAI_EasyAIInterface>
      - EAIMobSample
        <https://github.com/bbc-mc/EAIMobSample>
    - MC1.4.2
      - EasyAIInterfaceMP
        <https://github.com/bbc-mc/EasyAIInterfaceMP>
      - EAIMobSampleMP
        <https://github.com/bbc-mc/EAIMobSampleMP>

## 更新
  2012/11/11
        EasyAIInterface α2バージョン の MC1.4.2 実装を公開

----------
####Copyright &copy; 2012 bbc_mc (bbc-mc on github)
####Dual licensed under the [MIT license][MIT] and [GPL license][GPL].

[MIT]: http://www.opensource.org/licenses/mit-license.php
[GPL]: http://www.gnu.org/licenses/gpl.html
[YoujoMOD]: http://forum.minecraftuser.jp/viewtopic.php?f=13&t=2816#p20049
[topic]: http://forum.minecraftuser.jp/viewtopic.php?f=13&t=6134&p=49598#p49598
[github_EAI]: https://github.com/bbc-mc/EasyAIInterface
[github_EAIMP]: https://github.com/bbc-mc/EasyAIInterfaceMP
[github_EAIMobSample]: https://github.com/bbc-mc/EAIMobSample
[github_YoujoAI]: https://github.com/bbc-mc/YoujoAI_EasyAIInterface

