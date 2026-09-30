library verilog;
use verilog.vl_types.all;
entity PROYECTO_TOP_vlg_sample_tst is
    port(
        CLK             : in     vl_logic;
        CLOCK_50        : in     vl_logic;
        Reset           : in     vl_logic;
        SIM_MODE        : in     vl_logic;
        sampler_tx      : out    vl_logic
    );
end PROYECTO_TOP_vlg_sample_tst;
