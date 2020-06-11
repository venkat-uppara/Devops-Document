# Devops-Document

val lines = sc.textFile("hdfs:///user/venkat/ugamtest.txt", 8)
val words = lines.flatMap(x => x.split(" "))
val units = words.map ( word => (word, 1) ).persist(StorageLevel.MEMORY_ONLY)
val counts = units.reduceByKey ( (x, y) => x + y )
counts.collect()
