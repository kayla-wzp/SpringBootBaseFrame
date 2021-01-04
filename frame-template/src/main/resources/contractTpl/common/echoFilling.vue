<template>
    <el-dialog
        title="回显填充配置"
        :visible.sync="showEchoFilling"
        :close-on-click-modal="false"
        width="1200px"
        @close="clearDialog()"
    >
        <el-form :model="searchForm" ref="searchForm" :rules="rules">
            <el-row type="flex" class="row-bg" justify="space-around" style="align-items: center">
                <el-col :span="8">
                    <el-form-item label="对象属性名" prop="objectAttributeName">
                        <el-input v-model="searchForm.objectAttributeName"> </el-input>
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
            <el-table-column align="left" label="对象属性名" min-width="100" prop="objectAttributeName">
            </el-table-column>
            <el-table-column align="left" label="对象类型" min-width="100" prop="objectTypeText"> </el-table-column>
            <el-table-column align="left" label="查询SQL" min-width="100" prop="querySql"> </el-table-column>
            <el-table-column align="center" label="操作" width="200px">
                <template slot-scope="scope">
                    <el-button type="text" size="small" @click="editConfig(scope.row.templateId)">
                        <icon name="edit"></icon>
                        修改
                    </el-button>
                    <el-button type="text" size="small">
                        <i class="el-icon-delete" style="margin-right: 10%"></i>删除
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
        <echo-filling-dialog ref="echoFillingDialog"></echo-filling-dialog>
    </el-dialog>
</template>

<script>
import echoFillingDialog from '@/views/contractTpl/common/echoFillingDialog'

export default {
    name: 'echoFilling',
    components: {
        echoFillingDialog
    },
    data() {
        return {
            showEchoFilling: false,
            fileTemplateId: '',
            searchForm: {
                objectAttributeName: ''
            },
            rules: {},
            tableList: [{}],
            pageSize: 10,
            currentPage: 1,
            totalCount: 0
        }
    },
    methods: {
        openDialog(id) {
            this.fileTemplateId = id
            this.showEchoFilling = true
            this.queryAllList()
        },
        clearDialog() {
            this.fileTemplateId = ''
            this.currentPage = 1
            this.totalCount = 0
            this.tableList = []
            this.$refs['searchForm'].resetFields()
        },
        searchParam() {
            this.$refs['searchForm'].validate(valid => {
                if (valid) {
                    this.queryAllList('1')
                } else {
                    return false
                }
            })
        },
        // 获取列表数据
        queryAllList(currentPage) {
            if (currentPage) {
                this.currentPage = 1
            }
            const param = {
                ...this.searchForm,
                fileTemplateId: this.fileTemplateId,
                currentPage: this.currentPage,
                pageSize: this.pageSize
            }
            this.ajax(this.apiType().queryBusinessDataEchoConfigList, this.serviceType().query, param, responseData => {
                const data = responseData.body
                this.totalCount = Number(responseData.totalSize)
                this.tableList = data
            })
        },
        // 翻页
        handleCurrentChange(currentPage) {
            this.$emit('currentPageChange', currentPage)
            this.currentPage = currentPage
            this.queryAllList()
        },
        // 新增
        addConfig() {
            this.$refs.echoFillingDialog.openDialog(this.fileTemplateId)
        },
        // 编辑
        editConfig(id) {
            this.$refs.echoFillingDialog.openDialog(this.fileTemplateId, id)
        }
    }
}
</script>

<style scoped></style>
