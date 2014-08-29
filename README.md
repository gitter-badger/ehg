ehg(WIP)
========

Scala API for reading E-Hentai Galleries

    import ehg.impl.ExReader
    
    val ex = new ExReader(YourPreferences)
    val g = ex.read(EhgKey(134244, "ab0a96da70")).head
    
    println(s"Japanese title: ${g.titleJP}") // and more items!
    g.images.foreach(println) // the gallery's all images' URL