(func early (x)
  (cond (less x 0) (return -1) (times x 2)))
(early 5)
(early -1)

(prog ()
  (setq x 5)
  (return 100)
  (plus x 50))

(prog (i)
  (setq i 0)
  (while true
    (prog ()
      (cond (equal i 3) (break))
      (setq i (plus i 1))))
  i)
  
(prog ()
  (return 50)
  (plus 1 1))