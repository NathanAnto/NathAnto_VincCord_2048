import hevs.graphics.FunGraphics

import java.awt.Color
import java.awt.event.{KeyAdapter, KeyEvent}
import scala.util.Random

class G2048 {
  // Element de base
  var tabgame: Array[Array[Int]] = Array.ofDim(4, 4)
  var peuxdeplace: Boolean = true
  // Initialisation du tableau
  for (i <- tabgame.indices) {
    for (j <- tabgame(i).indices) {
      tabgame(i)(j) = 0
    }
  }


  // fungraphics
  val fg: FunGraphics = new FunGraphics(600, 600)
  var couCase = Color.white
  // variable base
  var mvmX = 0
  var mvmY = 1
  var lemptyX: List[Int] = List()
  var lemptyY: List[Int] = List()
  var nbalea = 0
  var yalea = 0
  var score = 0


  // Début du jeux
  tabgame(0)(0) = 2
  // affichage premier tableau
  affijeux()

  // Utilisation des touches
  fg.setKeyManager(new KeyAdapter() { // Will be called when a key has been pressed
    override def keyPressed(e: KeyEvent): Unit = {
      if (e.getKeyCode == KeyEvent.VK_RIGHT) {
        mvmX = 1
        mvmY = 0
        deplajeux()
        nouveaunbre()
        affijeux()
      }
      if (e.getKeyCode == KeyEvent.VK_DOWN) {
        mvmX = 0
        mvmY = 1
        deplajeux()
        nouveaunbre()
        affijeux()
      }
      if (e.getKeyCode == KeyEvent.VK_UP) {
        mvmX = 0
        mvmY = -1
        deplajeux()
        nouveaunbre()
        affijeux()
      }
      if (e.getKeyCode == KeyEvent.VK_LEFT) {
        mvmX = -1
        mvmY = 0
        deplajeux()
        nouveaunbre()
        affijeux()
      }
    }
  })


  // Déplacement jeux
  def deplajeux(): Unit = {
    for (z <- 0 until 3) {
      peuxdeplace = true
      while (peuxdeplace) {
        for (i <- tabgame.indices) {
          for (j <- tabgame(i).indices) {
            // deplacement case vide
            try {
              if (tabgame(i)(j) != 0 && tabgame(i + mvmY)(j + mvmX) == 0) {
                tabgame(i + mvmY)(j + mvmX) = tabgame(i)(j)
                tabgame(i)(j) = 0
                yalea += 1
              }
            } catch {
              case e => peuxdeplace = false
            }
            // addition de case
            try {
              // && tabgame(i)(j) != tabgame(i + mvmY * 2)(j + mvmX * 2) || temp marche pas
              /*if(tabgame(i)(j) != 0 && tabgame(i)(j) == tabgame(i + mvmY)(j + mvmX) && tabgame(i + (mvmY * 2))(j + (mvmX * 2)) == tabgame(i)(j)){
                tabgame(i + (mvmY * 2))(j + (mvmX * 2)) = tabgame(i + mvmY)(j + mvmX)
                score += tabgame(i + mvmY)(j + mvmX) * 2
                tabgame(i + mvmY)(j + mvmX) = 0
                yalea += 1
              } else*/ if (tabgame(i)(j) != 0 && tabgame(i)(j) == tabgame(i + mvmY)(j + mvmX) ) {
                tabgame(i + mvmY)(j + mvmX) = tabgame(i)(j) * 2
                score += tabgame(i)(j) * 2
                tabgame(i)(j) = 0
                yalea += 1
              }
            } catch {
              case f => print("")
            }
          }
        }
      }
    }
  }


  // nouveau nombre (viens après déplacement)
  def nouveaunbre(): Unit = {
    if(yalea != 0){
      for (i <- tabgame.indices) {
        for (j <- tabgame(i).indices) {
          if (tabgame(i)(j) == 0) {
            lemptyX = lemptyX :+ j
            lemptyY = lemptyY :+ i
          }
        }
      }
      nbalea = Random.nextInt(lemptyX.length - 1)
      tabgame(lemptyY(nbalea))(lemptyX(nbalea)) = 2
      lemptyX = List()
      lemptyY = List()
    }
    yalea = 0

  }


  // affichage jeux
  def affijeux(): Unit = {
    println("")
    for (i <- tabgame.indices) {
      for (j <- tabgame(i).indices) {
        // print(s"${tabgame(i)(j)} ")
        tabgame(i)(j) match {
          case 0 => couCase = Color.white
          case 2 => couCase = Color.lightGray
          case 4 => couCase = Color.GRAY
          case 8 => couCase = Color.BLUE
          case 16 => couCase = Color.CYAN
          case 32 => couCase = Color.red
          case 64 => couCase = Color.green
          case 128 => couCase = Color.darkGray
          case 256 => couCase = Color.magenta
          case 512 => couCase = Color.orange
          case 1024 => couCase = Color.pink
          case 2048 => couCase = Color.yellow
          case 4096 => couCase = Color.BLACK
        }
        fg.setColor(couCase)
        fg.drawFillRect(200 + 50*j,200+50*i,50,50)
        fg.setColor(Color.black)
        fg.drawRect(200 + 50*j,200+50*i,50,50)
        //fg.drawFancyString(215 + 50*j,225+50*i,tabgame(i)(j).toString,Color.BLACK,20)
        fg.drawString(218 + 50*j,230+50*i,tabgame(i)(j).toString,Color.BLACK,20)
        fg.setColor(Color.WHITE)
        fg.drawFillRect(145,20,120,40)
        fg.drawString(30,50,s"score: $score",Color.BLACK,40)

      }
      println("")
    }
  }

}
