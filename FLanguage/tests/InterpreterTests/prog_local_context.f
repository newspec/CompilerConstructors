(prog ()
  (setq x 5)
  (plus x 10))

(prog (a b)
  (setq a 10)
  (setq b 20)
  (plus a b))

(prog (local)
  (setq local 99)
  local)

(prog ()
  (setq x 5)
  (prog (y)
    (setq y 10)
    (plus x y)))
    
local