cmake_minimum_required(VERSION 2.8)

project (clips)
#set(CMAKE_BUILD_TYPE Release)
set(CMAKE_BUILD_TYPE Debug)

include_directories(/home/rompg/rom/packs/jdk-13.0.1/include)
include_directories(/home/rompg/rom/packs/jdk-13.0.1/include/linux)
link_directories(${CMAKE_SOURCE_DIR}/lib)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

FILE(GLOB sources ${CMAKE_SOURCE_DIR}/*.c)

add_library(clips SHARED ${sources})
