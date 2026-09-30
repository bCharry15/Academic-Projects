onerror {quit -f}
vlib work
vlog -work work PROYECTO_TOP.vo
vlog -work work PROYECTO_TOP.vt
vsim -novopt -c -t 1ps -L cycloneiii_ver -L altera_ver -L altera_mf_ver -L 220model_ver -L sgate work.PROYECTO_TOP_vlg_vec_tst
vcd file -direction PROYECTO_TOP.msim.vcd
vcd add -internal PROYECTO_TOP_vlg_vec_tst/*
vcd add -internal PROYECTO_TOP_vlg_vec_tst/i1/*
add wave /*
run -all
