library verilog;
use verilog.vl_types.all;
entity PROYECTO_TOP_vlg_check_tst is
    port(
        ACC             : in     vl_logic_vector(7 downto 0);
        Cout            : in     vl_logic;
        HEX0            : in     vl_logic_vector(6 downto 0);
        HEX1            : in     vl_logic_vector(6 downto 0);
        PC              : in     vl_logic_vector(2 downto 0);
        PULSO           : in     vl_logic;
        ROM_OUT         : in     vl_logic_vector(10 downto 0);
        sampler_rx      : in     vl_logic
    );
end PROYECTO_TOP_vlg_check_tst;
