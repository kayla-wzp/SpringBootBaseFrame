<template>
    <el-dialog
        title="手动输入字段配置"
        :visible.sync="showManualInput"
        :close-on-click-modal="false"
        width="1200px"
        @close="clearDialog()"
    >
        <el-form :model="searchForm" ref="searchForm" :rules="rules">
            <el-row type="flex" class="row-bg" justify="space-around" style="align-items: center">
                <el-col :span="8">
                    <el-form-item label="属性名" prop="propertyName">
                        <el-input v-model="searchForm.propertyName"> </el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="字段名称" prop="propertyName">
                        <el-input v-model="searchForm.propertyName"> </el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-button size="small" type="primary" @click="searchParam">
                        <icon name="search"></icon>
                        查询
                    </el-button>
                </el-col>
            </el-row>
            <el-row :gutter="0">
                <el-button type="primary" size="small" class="filter-item" icon="plus" @click="addConfig">
                    <icon name="plus"></icon>
                    新增
                </el-button>
            </el-row>
        </el-form>
        <el-table
            style="margin-top: 10px"
            :data="tableList"
            element-loading-text="加载中"
            element-loading-spinner="el-icon-loading"
            element-loading-background="rgba(0, 0, 0, 0.8)"
            border
            fit
            stripe
            highlight-current-row
            tooltip-effect="dark"
        >
            <el-table-column align="center" label="序号" width="60">
                <template slot-scope="scope">
                    <span>{{ (currentPage - 1) * pageSize + scope.$index + 1 }}</span>
                </template>
            </el-table-column>
            <el-table-column align="left" label="字段名称" min-width="100">
                <template slot-scope="scope">
                    <span>{{ scope.row.permitId }}</span>
                </template>
            </el-table-column>
            <el-table-column align="left" label="属性名" min-width="100">
                <template slot-scope="scope">
                    <span>{{ scope.row.parentPermitId }}</span>
                </template>
            </el-table-column>
            <el-table-column align="left" label="字段类型" min-width="100">
                <template slot-scope="scope">
                    <span>{{ scope.row.name }}</span>
                </template>
            </el-table-column>
            <el-table-column align="left" label="查询sql" min-width="100">
                <template slot-scope="scope">
                    <span>{{ scope.row.permitType }}</span>
                </template>
            </el-table-column>
            <el-table-column align="left" label="格式化规则" min-width="100">
                <template slot-scope="scope">
                    <span>{{ scope.row.permitType }}</span>
                </template>
            </el-table-column>
            <el-table-column align="left" label="金额转大写对应属性" min-width="100">
                <template slot-scope="scope">
                    <span>{{ scope.row.permitType }}</span>
                </template>
            </el-table-column>
            <el-table-column align="left" label="是否必填" min-width="100">
                <template slot-scope="scope">
                    <span>{{ scope.row.permitType }}</span>
                </template>
            </el-table-column>
            <el-table-column align="left" label="默认值" min-width="100">
                <template slot-scope="scope">
                    <span>{{ scope.row.permitType }}</span>
                </template>
            </el-table-column>
            <el-table-column align="left" label="校验正则" min-width="100">
                <template slot-scope="scope">
                    <span>{{ scope.row.permitType }}</span>
                </template>
            </el-table-column>
            <el-table-column align="left" label="页面展示顺序" min-width="100">
                <template slot-scope="scope">
                    <span>{{ scope.row.permitType }}</span>
                </template>
            </el-table-column>
            <el-table-column align="left" label="备注" min-width="100">
                <template slot-scope="scope">
                    <span>{{ scope.row.permitType }}</span>
                </template>
            </el-table-column>
            <el-table-column align="center" label="操作" width="280px" fixed="right">
                <template slot-scope="scope">
                    <el-button type="text" size="small" @click="editConfig(scope.row.id)">
                        <icon name="edit"></icon>
                        修改
                    </el-button>
                    <el-button type="text" size="small">
                        <i class="el-icon-delete" style="margin-right: 10%"></i>删除
                    </el-button>
                    <el-button type="text" size="small" @click="enumConfig">
                        配置枚举
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <div style="text-align: right">
            <el-pagination
                @current-change="handleCurrentChange"
                small
                :current-page="currentPage"
                :page-size="pageSize"
                layout="total, prev, pager, next, jumper"
                :total="totalCount"
            >
            </el-pagination>
        </div>
        <!--新增/修改手动输入配置弹窗-->
        <manual-input-dialog ref="manualInputDialog"></manual-input-dialog>
        <!--新增/修改枚举配置弹窗-->
        <enum-dialog ref="enumDialog"></enum-dialog>
    </el-dialog>
</template>

<script>
import manualInputDialog from '@/views/contractTpl/common/manualInputDialog'
import enumDialog from '@/views/contractTpl/common/enumDialog'

export default {
    name: 'manualInput',
    components: {
        manualInputDialog,
        enumDialog
    },
    data() {
        return {
            showManualInput: false,
            searchForm: {
                propertyName: ''
            },
            rules: {},
            tableList: [{}],
            pageSize: 10,
            currentPage: 1,
            totalCount: 0
        }
    },
    methods: {
        openDialog() {
            this.showManualInput = true
            this.queryAllList()
        },
        clearDialog() {},
        searchParam() {},
        // 获取列表数据
        queryAllList() {},
        // 翻页
        handleCurrentChange(currentPage) {
            this.$emit('currentPageChange', currentPage)
            this.currentPage = currentPage
            this.queryAllList()
        },
        // 新增
        addConfig() {
            this.$refs.manualInputDialog.openDialog()
        },
        // 编辑
        editConfig(id) {
            this.$refs.manualInputDialog.openDialog(id)
        },
        // 枚举配置
        enumConfig(id) {
            this.$refs.enumDialog.openDialog(id)
        }
    }
}
</script>

<style scoped></style>
