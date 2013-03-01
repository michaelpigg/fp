val fib: (Int => Long) = a => {
  a match {
    case 0 => 0
    case 1 => 1
    case _ => fib(a-2) + fib(a-1)
  }
}

def now = System.currentTimeMillis

def elapsed(f: => Unit): Long = {
  val start = now
  f
  now - start
}
